import { useEffect, useState, useCallback } from "react"
import { apiFetch } from "../api/apiFetch";
import { safeAwait } from "../lib/safeAwait";

export function useUserById(userId) {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchUser = useCallback(async () => {
        setLoading(true);
        const [err, data] = await safeAwait(
            apiFetch(`/api/user/${userId}`, {
                method: "GET",
            })
        );
        if (err) {
            setError(err.message);
            setLoading(false);
            return;
        }
        setUser(data || null);
        setError(null);
        setLoading(false);

    }, [userId]);
    useEffect(() => {
        fetchUser();
    }, [fetchUser]);
    return { user, loading, error, refetch: fetchUser };
}