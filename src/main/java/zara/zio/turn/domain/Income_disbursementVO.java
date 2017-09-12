package zara.zio.turn.domain;

import java.sql.Timestamp;

public class Income_disbursementVO {
	
	private int expense_Code ;
	private String expense_Content ;
	private int expense_Cost ;
	private Timestamp expense_Date ;
	
	private String user_id ;
	
	private int coin_Limit ;
	private String sc_Division ;

	public String getExpense_Content() {
		return expense_Content;
	}

	public void setExpense_Content(String expense_Content) {
		this.expense_Content = expense_Content;
	}

	public int getExpense_Cost() {
		return expense_Cost;
	}

	public void setExpense_Cost(int expense_Cost) {
		this.expense_Cost = expense_Cost;
	}

	public Timestamp getExpense_Date() {
		return expense_Date;
	}

	public void setExpense_Date(Timestamp expense_Date) {
		this.expense_Date = expense_Date;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getExpense_Code() {
		return expense_Code;
	}

	public void setExpense_Code(int expense_Code) {
		this.expense_Code = expense_Code;
	}

	public int getCoin_Limit() {
		return coin_Limit;
	}

	public void setCoin_Limit(int coin_Limit) {
		this.coin_Limit = coin_Limit;
	}

	public String getSc_Division() {
		return sc_Division;
	}

	public void setSc_Division(String sc_Division) {
		this.sc_Division = sc_Division;
	}
	
	
}
