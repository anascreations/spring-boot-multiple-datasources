package com.multiple.datasource.entity.secondary;

import java.io.Serializable;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "source", schema = "integration")
public class SourceEntity implements Serializable {
	private static final long serialVersionUID = -4337062132697461635L;
	@Id
	@Column(name = "source_id")
	private Long sourceId;
	private String sourceName;
	private String sourceType;
}
