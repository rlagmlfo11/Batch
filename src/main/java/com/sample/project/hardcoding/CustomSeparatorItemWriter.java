package com.sample.project.hardcoding;

import java.util.Collections;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;

import com.sample.project.entity.ResultTable;

public class CustomSeparatorItemWriter implements ItemWriter<ResultTable>, ItemStream {

	private final ItemWriter<ResultTable> delegate;
	private boolean separatorWritten = false;

	public CustomSeparatorItemWriter(ItemWriter<ResultTable> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void write(List<? extends ResultTable> items) throws Exception {
		// Delegate writing to the original writer
		delegate.write(items);

		// After writing items, if the separator has not been written, write it
		if (!separatorWritten) {
			ResultTable separator = new ResultTable();
			// Set the desired fields to null and "line changer" accordingly
			separator.setSeibetu("line changer");
			delegate.write(Collections.singletonList(separator));
			separatorWritten = true;
		}
	}

	// Implement the ItemStream interface methods to delegate to the wrapped writer
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).open(executionContext);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).update(executionContext);
		}
	}

	@Override
	public void close() throws ItemStreamException {
		if (delegate instanceof ItemStream) {
			((ItemStream) delegate).close();
		}
	}
}
