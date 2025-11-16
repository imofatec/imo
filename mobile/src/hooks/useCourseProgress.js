import { useEffect, useState, useCallback } from "react"
import { apiFetch } from "../api/apiFetch";
import { safeAwait } from "../lib/safeAwait";

export function useCourseProgress(page = 0, size = 10) {
    const [courses, setCourses] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchCourse = useCallback(async () => {
        setLoading(true);

        const query = new URLSearchParams({
            page,
            size
        }).toString();

        const [err, data] = await safeAwait(
            apiFetch(`/api/progress/details?${query}`, {
                method: "GET",
            })
        );
        if (err) {
            setError(err.message);
            setLoading(false);
            return;
        }
        setCourses(data || null);
        setError(null);
        setLoading(false);

    }, [page, size]);
    useEffect(() => {
        fetchCourse();
    }, [fetchCourse]);
    return { courses, loading, error, refetch: fetchCourse };
}