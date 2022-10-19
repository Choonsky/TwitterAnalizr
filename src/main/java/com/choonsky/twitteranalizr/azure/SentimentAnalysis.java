package com.choonsky.twitteranalizr.azure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentimentAnalysis {

	private TextDocument document;

	private String sentiment;
	
}
