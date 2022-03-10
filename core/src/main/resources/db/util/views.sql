drop view user_request_view;
create or replace view user_request_view as
select user_request.unique_qrd_request_id,
       user_request.request_date,
       user_request.local_scan_time,
       user_request.anonymized_ip,
       user_request.lat,
       user_request.lng,
       user_request.accuracy,
       user_request.browser,
       user_request.browser_version,
       user_request.os,
       user_request.os_version,
       user_request.timezone,
       user_request.model,
       user_request.brand,
       qc.qrd_id,
       qc.short_url,
       qc.folder,
       qc.scans,
       qc.unique_visitors,
       qc.creation_date,
       qc.requests_offset,
       qc.tags,
       qc.title,
       qc.description,
       cl.country,
       cl.state,
       cl.district,
       cl.sub_district,
       cl.block,
       cl.panchayat,
       cl.village_city,
       cl.number_of_times_looked_up
from user_request
         join qr_code qc on user_request.qr_code_id = qc.id
         left outer join coded_location cl on user_request.coded_location_id = cl.id;

GRANT SELECT ON ALL TABLES IN SCHEMA public TO reports;
