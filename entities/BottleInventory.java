package org.example.Entities;

import jakarta.persistence.*;
import org.example.Bottle;

import java.util.Map;

@Entity
@Table (name = "bottleInventory")
public class BottleInventory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Using Map to associate bottle type with its count
    @ElementCollection
    @CollectionTable(name = "bottle_counts", joinColumns = @JoinColumn(name = "bottle_inventory_id"))
    @Enumerated(EnumType.STRING)  // Store the enum as a string
    @Column(name = "count")  // Column for the count of each bottle type
    private Map<Bottle, Integer> bottles;//Type of bottle and count of it
}
