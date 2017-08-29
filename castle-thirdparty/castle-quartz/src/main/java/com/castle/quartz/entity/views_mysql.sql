DROP VIEW
IF EXISTS V_QRTZ_JOB;

DROP VIEW
IF EXISTS V_QRTZ_TRIGGER;

CREATE VIEW V_QRTZ_JOB AS (
	SELECT
		CAST(
			CONCAT(
				T.SCHED_NAME,
				'-',
				T.JOB_GROUP,
				'-',
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

CREATE VIEW V_QRTZ_TRIGGER AS (
	SELECT
		CAST(
			CONCAT(
				T.SCHED_NAME,
				'-',
				T.TRIGGER_GROUP,
				'-',
				T.TRIGGER_NAME
			) AS CHAR (1000)
		) id,
		T.SCHED_NAME sched_name,
		T.TRIGGER_GROUP trigger_group,
		T.TRIGGER_NAME trigger_name,
		T.JOB_GROUP job_group,
		T.JOB_NAME job_name,
		T.DESCRIPTION description,
		CAST(
			FROM_UNIXTIME(T.NEXT_FIRE_TIME / 1000) AS DATETIME
		) next_fire_time,
		CAST(
			FROM_UNIXTIME(T.PREV_FIRE_TIME / 1000) AS DATETIME
		) prev_fire_time,
		T.PRIORITY priority,
		T.TRIGGER_STATE trigger_state,
		T.TRIGGER_TYPE trigger_type,
		CAST(
			FROM_UNIXTIME(T.START_TIME / 1000) AS DATETIME
		) start_time,
		CAST(
			FROM_UNIXTIME(T.END_TIME / 1000) AS DATETIME
		) end_time,
		T.MISFIRE_INSTR misfire_instr
	FROM
		QRTZ_TRIGGERS T
);