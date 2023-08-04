package com.codesherpa.beerdispenser.app.models;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Serving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp startTime;

    private Timestamp endTime;

    @Column(name="beer_id")
    private Long beerId;

    @Column(name="tap_id")
    private Long tapId;

    @Column(name="promoter_id")
    private Long promoterId;

    @Column(name="attendee_id")
    private Long attendeeId;

    @Column(name="flow_per_second")
    private Float flowPerSecond;

    @Column(name="price_per_litre")
    private Float pricePerLitre;

    private Float total;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}