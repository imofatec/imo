import { useEffect, useState, useCallback } from "react"
import { apiFetch } from "../api/apiFetch";
import { safeAwait } from "../lib/safeAwait";

export function useLessonComments({ lessonId, page = 0, size = 10 } = {}) {
    const [comments, setComments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchComments = useCallback(async () => {
        setLoading(true);

        const [err, data] = await safeAwait(
            apiFetch(`/api/comment/${lessonId}?page=${page}&size=${size}`, {
                method: "GET",
            })
        );

        if (err) {
            setError(err.message);
            setLoading(false);
            return;
        }
        setComments(data);
        setError(null);
        setLoading(false);
    }, [lessonId, page, size]);

    useEffect(() => {
        fetchComments();
    }, [fetchComments]);
    return { comments, loading, error, refetch: fetchComments };
}