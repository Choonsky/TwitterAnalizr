package com.choonsky.twitteranalizr.azure;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TextAnalyticsRequest {

	private List<TextDocument> documents = new ArrayList<>();

}
