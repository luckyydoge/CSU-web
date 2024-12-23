package edu.csu.web_last.trigger.http.type;

import lombok.Data;

import java.util.List;

@Data
public class NewsListResponse {
    List<String> news;
}
