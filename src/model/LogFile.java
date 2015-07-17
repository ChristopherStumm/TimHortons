package model;

public class LogFile {

	private float a1;
	private float a2;
	private float b1;
	private float b2;
	private float em1;
	private float em2;
	private String overallStatus;
	private long ts_start;
	private long ts_stop;

	public float getA1() {
		return a1;
	}

	public void setA1(float a1) {
		this.a1 = a1;
	}

	public float getA2() {
		return a2;
	}

	public void setA2(float a2) {
		this.a2 = a2;
	}

	public float getB1() {
		return b1;
	}

	public void setB1(float b1) {
		this.b1 = b1;
	}

	public float getB2() {
		return b2;
	}

	public void setB2(float b2) {
		this.b2 = b2;
	}

	public float getEm1() {
		return em1;
	}

	public void setEm1(float em1) {
		this.em1 = em1;
	}

	public float getEm2() {
		return em2;
	}

	public void setEm2(float em2) {
		this.em2 = em2;
	}

	public String getOverallStatus() {
		return overallStatus;
	}

	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	public long getTs_start() {
		return ts_start;
	}

	public void setTs_start(long ts_start) {
		this.ts_start = ts_start;
	}

	public long getTs_stop() {
		return ts_stop;
	}

	public void setTs_stop(long ts_stop) {
		this.ts_stop = ts_stop;
	}
}
