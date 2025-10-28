package org.example.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "WineManager")
public class WineManager
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wineManager")
    private List<Wine> wines;//collection of wine recipes
}
