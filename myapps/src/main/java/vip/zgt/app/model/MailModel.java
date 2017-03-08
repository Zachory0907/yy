package vip.zgt.app.model;

public class MailModel {

	private String receiverAccount;
	private String receiverNickname;
	private String senderSubject;
	private String senderContent;

	public MailModel(String receiverAccount, String receiverNickname, String senderSubject, String senderContent) {
		this.receiverAccount = receiverAccount;
		this.receiverNickname = receiverNickname;
		this.senderSubject = senderSubject;
		this.senderContent = senderContent;
	}

	public String getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount) {
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverNickname() {
		return receiverNickname;
	}

	public void setReceiverNickname(String receiverNickname) {
		this.receiverNickname = receiverNickname;
	}

	public String getSenderSubject() {
		return senderSubject;
	}

	public void setSenderSubject(String senderSubject) {
		this.senderSubject = senderSubject;
	}

	public String getSenderContent() {
		return senderContent;
	}

	public void setSenderContent(String senderContent) {
		this.senderContent = senderContent;
	}

}
