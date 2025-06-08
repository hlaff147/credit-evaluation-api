package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.out.persistence.entity;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private double income;

    @ElementCollection
    @CollectionTable(name = "client_credit_types", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "credit_type")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<CreditType> eligibleCreditTypes = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static ClientEntity fromDomain(Client client) {
        return ClientEntity.builder()
                .id(client.getId())
                .name(client.getName())
                .age(client.getAge())
                .income(client.getIncome())
                .eligibleCreditTypes(client.getEligibleCreditTypes())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

    public Client toDomain() {
        return Client.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .income(this.income)
                .eligibleCreditTypes(this.eligibleCreditTypes)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}