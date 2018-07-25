package com.playbuzz.common.entities

case class EnrichedEvent(id: String,
                         ts: Long,
                         readableTS: java.util.Date,
                         complexFooBar: String)
