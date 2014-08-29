package com.example.elements;

public class Dummy {

	private String text;
	private String name;
	private String screen_name;
	private String time;
	private String image_url;

	public Dummy(String text, String time, String name, String screen_name,
			String image_url) {

		this.text = text;
		this.time = time;
		this.name = name;
		this.screen_name = screen_name;
		this.image_url = image_url;

	}

	public String getText() {
		return this.text;
	}

	public String getName() {
		return this.name;
	}

	public String getScreenName() {
		return this.screen_name;
	}

	public String getTime() {
		return this.time;
	}

	public String getImageUrl() {
		return this.image_url;
	}

}
