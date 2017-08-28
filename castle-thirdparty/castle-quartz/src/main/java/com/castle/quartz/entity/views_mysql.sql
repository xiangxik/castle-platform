DROP TABLE IF EXISTS V_QRTZ_JOB;


CREATE VIEW V_QRTZ_JOB AS (
	SELECT
		CAST(
			CONCAT(
				T.SCHED_NAME,
				'/',
				T.JOB_GROUP,
				'/',
				T.JOB_NAME
			) AS CHAR (1000)
		) id,
		T.SCHED_NAME sched_name,
		T.JOB_NAME job_name,
		T.JOB_GROUP job_group,
		T.JOB_CLASS_NAME job_class_name,
		T.DESCRIPTION description,
		T.IS_DURABLE is_durable,
		T.IS_NONCONCURRENT is_nonconcurrent,
		T.IS_UPDATE_DATA is_update_data,
		T.REQUESTS_RECOVERY requests_recovery
	FROM
		QRTZ_JOB_DETAILS T
);
