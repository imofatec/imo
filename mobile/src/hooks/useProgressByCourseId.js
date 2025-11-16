import { useEffect, useState, useCallback } from "react"
import { apiFetch } from "../api/apiFetch";
import { safeAwait } from "../lib/safeAwait";

export function useProgressByCourseId(courseId) {
    const [progress, setProgress] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchProgress = useCallback(async () => {
        if (!courseId) return;

        setLoading(true);
        const [err, data] = await safeAwait(
            apiFetch(`/api/progress/details/${courseId}`, {
                method: "GET",
            })
        );
        if (err) {
            setError(err.message);
            setLoading(false);
            return;
        }
        setProgress(data.progress || []);
        setError(null);
        setLoading(false);
    }, [courseId]);

    useEffect(() => {
        if (courseId) {
            fetchProgress();
        }
    }, [fetchProgress, courseId]);

    return { progress, loading, error, refetch: fetchProgress };
}
