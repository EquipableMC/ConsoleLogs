package site.equipable.consoleLogs.utils;

import org.apache.logging.log4j.core.LogEvent;
import org.bukkit.Bukkit;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimitHandler {
    static StringBuilder currentMessage = new StringBuilder();
    static final int maxLength = 1920;
    static volatile boolean isPending = false;
    static final int maxTimeBetweenLogs = 5; // seconds
    public static ExecutorService executorService = null;
    public static String lastLoggedMessage;
    public static volatile boolean shuttingDown = false;

    public static void shutdownExecutor() {
        shuttingDown = true;
        Bukkit.getLogger().info("[ConsoleLogs] Shutting down executor...");

        synchronized (RateLimitHandler.class) {
            isPending = false;
            currentMessage.setLength(0);
        }

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
            try {
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    Bukkit.getLogger().severe("[ConsoleLogs] Executor did not terminate!");
                } else {
                    Bukkit.getLogger().info("[ConsoleLogs] Executor terminated successfully.");
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public static void initializeExecutor() {
        if (shuttingDown) return;

        if (executorService == null || executorService.isShutdown()) {
            executorService = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r, "ConsoleLogs-Worker");
                t.setDaemon(true);
                return t;
            });
            System.out.println("[ConsoleLogs] Executor initialized");
        }
    }


    public static void insertNewLog(String message, LogEvent logEvent) {
        if (shuttingDown || message == null || message.isEmpty()) return;
        initializeExecutor();
        boolean submitWorker = false;

        synchronized (RateLimitHandler.class) {
            currentMessage.append(message);

            if (!isPending) {
                isPending   = true;
                submitWorker = true;
            }
        }

        if (submitWorker) {
            executorService.submit(() -> processMessages(logEvent));
        }
    }

    private static void processMessages(LogEvent logEvent) {
        while (!shuttingDown) {
            String nextChunk;
            boolean moreToSend;
            synchronized (RateLimitHandler.class) {
                int length = currentMessage.length();
                if (length == 0) {
                    isPending = false;
                    break;
                }
                if (length > maxLength) {
                    nextChunk   = currentMessage.substring(0, maxLength);
                    currentMessage.delete(0, maxLength);
                    moreToSend  = true;
                } else {
                    nextChunk   = currentMessage.toString();
                    currentMessage.setLength(0);
                    moreToSend  = false;
                }
            }
            handleSend(nextChunk, logEvent);
            if (!moreToSend) break;
            try {
                Thread.sleep(maxTimeBetweenLogs * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static void handleSend(String message, LogEvent logEvent) {
        String finalMsg = "```ansi\n" + message + "```";
        BukkitLogEvent bukkitLogEvent = new BukkitLogEvent(logEvent, finalMsg);
        Bukkit.getPluginManager().callEvent(bukkitLogEvent);
        lastLoggedMessage = finalMsg;

    }
}
