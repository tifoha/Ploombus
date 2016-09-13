package ua.tifoha.search.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vitaly on 11.09.2016.
 */
public abstract class AbstractWorker implements Runnable {
    protected final String name;
    protected final Logger LOGGER;
    protected volatile boolean cancelled;
    protected volatile boolean done;

    public AbstractWorker(String name) {
        this.name = name;
        LOGGER = LoggerFactory.getLogger(String.format("%s(%s)", this.getClass(), name));
    }

    public abstract void doWork() throws InterruptedException, Exception;

    @Override
    public void run() {
        onStart();
        try {
            while (!cancelled && !Thread.interrupted()) {
                doWork();
            }
        } catch (InterruptedException e) {
            handleInterruption(e);
        } catch (Exception e) {
            handleException(e);
        } finally {
            beforeEnd();
            done = true;
        }
    }

    protected void onStart() {
        LOGGER.info("Worker started.");
        //Lazy by default
    }

    protected void handleException(Exception e) {
        LOGGER.error("Worker {} cached Exception.", e);
        stop();
        //Lazy by default
    }

    protected void handleInterruption(InterruptedException e) {
        LOGGER.error("Worker {} Interrupted.", e);
        stop();
        //Lazy by default
    }

    protected void beforeEnd() {
        LOGGER.info("Worker died.");
        //Lazy by default
    }

    public String getName() {
        return name;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void stop() {
        cancelled = true;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return name;
    }
}
