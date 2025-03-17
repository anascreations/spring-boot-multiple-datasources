package com.multiple.ds.service.entity.primary;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "target", schema = "integration")
public class TargetEntity implements Serializable {
	private static final long serialVersionUID = 6828732961979814485L;
	@Id
	@Column(name = "target_id")
	private Long targetId;
	private String targetName;
	private String targetType;
}
