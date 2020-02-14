package com.example.demo.entity;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE_DATA")
public class MessageData {

	@Id
	@Column(name = "MESSAGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long messageId;
	
	@Lob
	@Column(name = "MESSAGE_DATA", nullable = false)
	private byte[] payload;
	
	@Column(name= "PUBLISHED_DATE", nullable = false)
	private Date published;
	
	@Column(name= "PROCESSED_DATE", nullable = false)
	private Date processedDate;

	public MessageData() {
		super();
	}

	public MessageData(byte[] payload, Date published, Date processedDate) {
		super();
		this.payload = payload;
		this.published = published;
		this.processedDate = processedDate;
	}
	
	public MessageData(Long messageId, byte[] payload, Date published, Date processedDate) {
		this(payload, published, processedDate);
		this.messageId = messageId;
	}
	
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	@Override
	public String toString() {
		return "MessageData [messageId=" + messageId + ", payload=" + Arrays.toString(payload) + ", published="
				+ published + ", processedDate=" + processedDate + "]";
	}
	
}
