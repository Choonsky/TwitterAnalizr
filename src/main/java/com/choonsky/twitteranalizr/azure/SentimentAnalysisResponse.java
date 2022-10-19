package com.choonsky.twitteranalizr.azure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentimentAnalysisResponse {

	private List<TextDocumentScore> documents = new ArrayList<>();

	@Data
	public static class TextDocumentScore {

		private String id;

		private String sentiment;

	}

}
