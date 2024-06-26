package com.gamesmart.simplechat.enghine.io;

public class Reply {
	private Session session;
	private Error error = Error.none;

	public enum Error{
		none,
		userNotExist,
		userNotLogin,
		handlerNotExit
	}
	

	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	public Session getSession() {
		return session;
	}
}
