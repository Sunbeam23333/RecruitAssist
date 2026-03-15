package com.recruitassist.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class JsonFileStore {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ConcurrentHashMap<Path, ReentrantReadWriteLock> pathLocks = new ConcurrentHashMap<>();

    public <T> List<T> readAll(Path directory, Class<T> clazz) {
        if (directory == null || Files.notExists(directory)) {
            return List.of();
        }

        try (Stream<Path> paths = Files.list(directory)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .sorted()
                    .map(path -> read(path, clazz))
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to read directory: " + directory, ex);
        }
    }

    public <T> T read(Path file, Class<T> clazz) {
        return read(file, (Type) clazz);
    }

    public <T> T read(Path file, Type type) {
        if (file == null || Files.notExists(file)) {
            return null;
        }

        Path normalized = normalize(file);
        ReentrantReadWriteLock lock = lockFor(normalized);
        lock.readLock().lock();
        try (Reader reader = Files.newBufferedReader(normalized, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, type);
        } catch (IOException | JsonSyntaxException ex) {
            throw new IllegalStateException("Failed to read JSON file: " + normalized, ex);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void write(Path file, Object value) {
        Path normalized = normalize(file);
        ReentrantReadWriteLock lock = lockFor(normalized);
        lock.writeLock().lock();
        try {
            Path parent = normalized.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            Path tempFile = Files.createTempFile(parent, normalized.getFileName().toString(), ".tmp");
            try (Writer writer = Files.newBufferedWriter(
                    tempFile,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING)) {
                gson.toJson(value, writer);
            }

            try {
                Files.move(tempFile, normalized, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(tempFile, normalized, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to write JSON file: " + normalized, ex);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void appendLine(Path file, String line) {
        Path normalized = normalize(file);
        ReentrantReadWriteLock lock = lockFor(normalized);
        lock.writeLock().lock();
        try {
            Path parent = normalized.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(
                    normalized,
                    line,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to append file: " + normalized, ex);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Path normalize(Path path) {
        return path.toAbsolutePath().normalize();
    }

    private ReentrantReadWriteLock lockFor(Path path) {
        return pathLocks.computeIfAbsent(path, unused -> new ReentrantReadWriteLock());
    }
}
