package com.choonsky.twitteranalizr.azure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextDocument {

	private String language;

	private String id;

	private String text;


}
