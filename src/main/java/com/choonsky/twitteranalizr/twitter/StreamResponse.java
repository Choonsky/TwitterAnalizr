package com.choonsky.twitteranalizr.twitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamResponse {

	private Tweet data;
	
	public static StreamResponse emptyResponse() {
		return new StreamResponse();
	}

}
