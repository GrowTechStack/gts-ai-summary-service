package com.gts.aisummary.global.kafka.dto;

public record SummaryRequestMessage(Long contentId, String title, String content) {}
