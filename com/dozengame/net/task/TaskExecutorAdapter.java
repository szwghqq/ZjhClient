package com.dozengame.net.task;
public abstract class TaskExecutorAdapter implements TaskExecutor {
	private boolean canceled = false;

	public void Cancel() {
		canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void onException(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(int result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinal(Task task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub

	}

}
