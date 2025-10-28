package org.example.Entities;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table (name = "wines")
public class Wine
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many wines can belong to one wine manager
    @JoinColumn(name = "wine_manager_id", nullable = false) // Foreign key column
    private WineManager wineManager; // Reference to the WineManager

    // Using @ElementCollection to store GrapeType and percentage
    @ElementCollection
    @CollectionTable(name = "wine_grapetypes", joinColumns = @JoinColumn(name = "wine_id"))
    @MapKeyJoinColumn(name = "grape_type_id") // Assuming you want to use a foreign key for GrapeType
    @Column(name = "percentage")
    private Map<Grapetype, Double> grapetypes;//Object of grapetype needed for the mix and the needed percentage for doint it
}