package Entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class T_ZB_PL2
{
	private String instr_no;
	private Timestamp dt;
	private BigDecimal displace_x;
	private BigDecimal displace_y;
	private Timestamp handle_time;
	private Boolean handle_result;
	private int people_handle;

	public String getInstr_no()
	{
		return instr_no;
	}

	public void setInstr_no(String instrNo)
	{
		instr_no = instrNo;
	}

	public Timestamp getDt()
	{
		return dt;
	}

	public void setDt(Timestamp dt)
	{
		this.dt = dt;
	}

	public BigDecimal getDisplace_x()
	{
		return displace_x;
	}

	public void setDisplace_x(BigDecimal displaceX)
	{
		displace_x = displaceX;
	}

	public BigDecimal getDisplace_y()
	{
		return displace_y;
	}

	public void setDisplace_y(BigDecimal displaceY)
	{
		displace_y = displaceY;
	}

	public Timestamp getHandle_time()
	{
		return handle_time;
	}

	public void setHandle_time(Timestamp handleTime)
	{
		handle_time = handleTime;
	}

	public Boolean getHandle_result()
	{
		return handle_result;
	}

	public void setHandle_result(Boolean handleResult)
	{
		handle_result = handleResult;
	}

	public int getPeople_handle()
	{
		return people_handle;
	}

	public void setPeople_handle(int peopleHandle)
	{
		people_handle = peopleHandle;
	}

}
