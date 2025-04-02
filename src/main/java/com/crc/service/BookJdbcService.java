package com.crc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookJdbcService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookJdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getBooksCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM crc.BOOKS", Integer.class);
    }

}
