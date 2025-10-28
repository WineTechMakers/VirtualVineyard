package org.example.Entities;

import jakarta.persistence.*;

@Entity
@Table (name = "grapetype")
public class Grapetype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false)
    private String name;//Name of grape
    @Column (nullable = false)
    private int quantity;//quantity in warehouse
    @Column (nullable = false)
    private boolean isBlack;//either black-0 or white-1
    @Column (nullable = false)
    private int product;//how much wine can be made per 1kg of grape
}