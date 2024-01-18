package com.isxcode.star.api.work.pojos.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateUdfReq {

	@Schema(title = "udf的id", example = "sy_48c4304593ea4897b6af999e48685896")
	@NotEmpty(message = "udf的id不能为空")
	private String id;

	@Schema(title = "udf类型", example = "UDF|UDAF")
	@NotEmpty(message = "udf类别不能为空")
	private String type;

	@Schema(title = "函数名称", example = "a1")
	@NotEmpty(message = "函数名称不能为空")
	private String funcName;

	@Schema(title = "udf文件类名称", example = "org.example.spark.udf.MyUdf")
	@NotEmpty(message = "类名称不能为空")
	private String className;

	@Schema(title = "函数返回值类型", example = "值列表：String|int|double|boolean|date")
	@NotEmpty(message = "返回值类型不能为空")
	private String resultType;

	@Schema(title = "启用状态", example = "启用为true，停用为false")
	private Boolean status;
}
