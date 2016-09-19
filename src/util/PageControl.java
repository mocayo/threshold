package util;

import java.util.List;

public class PageControl {
	
	private int cpage = 1;// 当前页数

	private int totalitem;// 总共条数

	private int totalpage;// 总页数

	private int pagesize = 10;// 每页显示条数

	private int nextpage;// 下一页

	private int previouspage;// 上一页

	private boolean hasnextpage;// 是否有下一页

	private boolean hasprevioupage;// 是否有上一也

	private boolean hasfristpage;// 是否有最前页

	private boolean hasendpage;// 是否有最后页

	private List list;// 现实的具体内容

	/**
	 * @return Returns the hasendpage.
	 */
	public boolean isHasendpage()
	{
		return hasendpage;
	}

	/**
	 * @param hasendpage
	 *            The hasendpage to set.
	 */
	public void setHasendpage(boolean hasendpage)
	{
		this.hasendpage = hasendpage;
	}

	/**
	 * @return Returns the hasfristpage.
	 */
	public boolean isHasfristpage()
	{
		return hasfristpage;
	}

	/**
	 * @param hasfristpage
	 *            The hasfristpage to set.
	 */
	public void setHasfristpage(boolean hasfristpage)
	{
		this.hasfristpage = hasfristpage;
	}

	public boolean isHasnextpage()
	{
		if (cpage < totalpage)
		{
			return true;
		}
		return false;
	}

	public void setHasnextpage(boolean hasnextpage)
	{
		this.hasnextpage = hasnextpage;
	}

	public boolean isHasprevioupage()
	{
		if (cpage != 1)
		{
			return true;
		}
		return false;
	}

	public void setHasprevioupage(boolean hasprevioupage)
	{
		this.hasprevioupage = hasprevioupage;
	}

	public int getPagesize()
	{
		return pagesize;
	}

	public void setPagesize(int pagesize)
	{
		this.pagesize = pagesize;
	}

	public int getCpage()
	{
		return cpage;
	}

	public void setCpage(int cpage)
	{
		this.cpage = cpage;
	}

	public List getList()
	{
		return list;
	}

	public void setList(List list)
	{
		this.list = list;
	}

	public int getNextpage()
	{

		if (isHasnextpage())
		{
			return cpage + 1;
		}
		return cpage;
	}

	public void setNextpage(int nextpage)
	{
		this.nextpage = nextpage;
	}

	public int getPreviouspage()
	{
		if (isHasprevioupage())
		{
			return cpage - 1;
		}
		return cpage;
	}

	public void setPreviouspage(int previouspage)
	{
		this.previouspage = previouspage;
	}

	public int getTotalitem()
	{
		return totalitem;
	}

	public void setTotalitem(int totalitem)
	{
		this.totalitem = totalitem;
		if (pagesize > 0)
		{
			totalpage = totalitem % pagesize > 0 ? totalitem / pagesize + 1
					: totalitem / pagesize;
		}
	}

	public int getTotalpage()
	{
		return totalpage;
	}

	public void setTotalpage(int totalpage)
	{
		this.totalpage = totalpage;
	}
}
