package org.example.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "hosts")
public class Host extends Person{
}
