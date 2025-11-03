import { useEffect, useState } from "react";
import { apiFetch } from "../api/apiFetch";
import { safeAwait } from "../lib/safeAwait";
export function useAllCategories({page = 0, size = 10} = {}) {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        async function fetchCategories() {
            setLoading(true);

            const query = new URLSearchParams({
                page,
                size,
            }).toString();

            const [err, data] = await safeAwait(
                apiFetch(`/api/course/categories?${query}`, {
                    method: "GET",
                })
            );
            if (err) {
                setError(err.message);
                setLoading(false);
                return;
            }
            setCategories(data || []);
            setError(null);
            setLoading(false);
        }
        fetchCategories();
    }, [page, size]);

    return { categories, loading, error };
}
