import { useEffect, useState, useCallback } from "react";
import { apiFetch } from "../api/apiFetch";
import { safeAwait } from "../lib/safeAwait";

export function useAllCourses({ matchType = "PERFECT", combineWith = "AND", page = 0, size = 10, categorySlug, nameSlug } = {}) {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchCourses = useCallback(async () => {
    setLoading(true);

    const query = new URLSearchParams({
      matchType,
      combineWith,
      page,
      size,
      ...(categorySlug ? { categorySlug } : {}),
      ...(nameSlug ? { nameSlug } : {})
    }).toString();

    const [err, data] = await safeAwait(
      apiFetch(`/api/course/search?${query}`, {
        method: "GET",
      })
    );

    if (err) {
      setError(err.message);
      setLoading(false);
      return;
    }
    setCourses(data || []);
    setError(null);
    setLoading(false);
  }, [matchType, combineWith, page, size, categorySlug, nameSlug]);

  useEffect(() => {
    fetchCourses();
  }, [fetchCourses]);

  return { courses, loading, error, refetch: fetchCourses };
}
