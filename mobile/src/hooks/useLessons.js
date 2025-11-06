import { useEffect, useState, useCallback } from "react"
import { apiFetch } from "../api/apiFetch";
import { safeAwait } from "../lib/safeAwait";

export function useLessons({ courseNameSlug, page = 0, size = 10 } = {}) {
    const [lessons, setLessons] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchLessons = useCallback(async () => {
        setLoading(true);

        const query = new URLSearchParams({
            page,
            size,
            ...(courseNameSlug ? { courseNameSlug } : {}),
        }).toString();

        const [err, data] = await safeAwait(
            apiFetch(`/api/lesson/search?${query}`, {
                method: "GET",
            })
        );

        if (err) {
            setError(err.message);
            setLoading(false);
            return;
        }

        setLessons(data || []);
        setError(null);
        setLoading(false);

    }, [courseNameSlug, page, size]);

    useEffect(() => {
        fetchLessons();
    }, [fetchLessons]);
    return { lessons, loading, error, refetch: fetchLessons };
}