package com.snailcatch.server.domain.query_log.repository;

import com.snailcatch.server.domain.query_log.QueryLog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueryLogRepository  extends MongoRepository<QueryLog, ObjectId> {
}
