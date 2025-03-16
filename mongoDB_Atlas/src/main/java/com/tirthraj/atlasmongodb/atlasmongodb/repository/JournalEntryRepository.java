package com.tirthraj.atlasmongodb.atlasmongodb.repository;

import com.tirthraj.atlasmongodb.atlasmongodb.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {}
