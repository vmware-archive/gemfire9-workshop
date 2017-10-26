package io.pivotal.listener;

import io.pivotal.listener.domain.Test;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;
import com.gemstone.gemfire.pdx.PdxInstance;

public class TestAsyncEventListener implements AsyncEventListener, Declarable
{
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public void close() {
        // TODO Auto-generated method stub

    }

    public void init(Properties arg0) {
        // TODO Auto-generated method stub

    }

    public boolean processEvents(List<AsyncEvent> entries)
    {
        logger.log (Level.INFO, String.format("TestAsyncEventListener : Size of List<AsyncEvent> = %s", entries.size()));

        // process the events here, could write to a database etc
        for (AsyncEvent ge: entries)
        {

             PdxInstance pdxInstance = (PdxInstance) ge.getDeserializedValue();
             Test test = (Test) pdxInstance.getObject();

             System.out.println(test.toString());
        }

        return true;
    }

}
