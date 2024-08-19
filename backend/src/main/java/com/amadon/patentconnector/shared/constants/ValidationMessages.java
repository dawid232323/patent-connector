package com.amadon.patentconnector.shared.constants;

public record ValidationMessages()
{
	public record CommonMessages() {
		public static final String NOT_NULL = "Field cannot be null";
		public static final String NOT_BLANK = "Field cannot be blank";
		public static final String EMAIL = "Please provide valid email address";
		public static final String PASSWORD = "Password should be at least 8 characters long, contain at least 1 capitalized character and at least 1 number, without special characters";
	}

	public record StringLengthMessages() {
		public static final String MAX_300_CHARACTERS = "Field should not be longer than 300 characters";
		public static final String MAX_500_CHARACTERS = "Field should not be longer than 500 characters";
	}
}
