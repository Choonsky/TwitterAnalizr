package com.choonsky.twitteranalizr.twitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamRuleRequest {

	private List<StreamRule> add = new ArrayList<>();

	public void addRule(String value, String tag) {
		this.add.add(new StreamRule(value,tag));
	}

}
