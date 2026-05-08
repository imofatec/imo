import { Search, X } from 'lucide-react'
import { useEffect, useRef, useState, type FormEvent } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import CourseModal from '@/components/AllCourses/CourseModal'
import { useCourses } from '@/hooks/useCourses'
import { useDebounce } from '@/hooks/useDebounce'
import { getCategorySlugFromSearchTerm } from '@/lib/categorySearch'
import type { Course } from '@/types/course'

export default function SearchBar() {
  const navigate = useNavigate()
  const [searchParams] = useSearchParams()
  const [searchTerm, setSearchTerm] = useState('')
  const [isInputFocused, setIsInputFocused] = useState(false)
  const [selectedCourse, setSelectedCourse] = useState<Course | null>(null)
  const inputRef = useRef<HTMLInputElement | null>(null)

  const trimmedSearchTerm = searchTerm.trim()
  const debouncedSearchTerm = useDebounce(trimmedSearchTerm, 500)
  const debouncedCategorySlug = getCategorySlugFromSearchTerm(debouncedSearchTerm)
  const shouldSearchPreview = isInputFocused && debouncedSearchTerm.length > 0
  const showPreview = isInputFocused && trimmedSearchTerm.length > 0

  const {
    courses: previewCourses,
    loading: previewLoading,
    error: previewError,
  } = useCourses({
    matchType: debouncedCategorySlug ? 'PERFECT' : 'CONTAINS',
    page: 0,
    size: 5,
    name: debouncedCategorySlug ? undefined : debouncedSearchTerm,
    categorySlug: debouncedCategorySlug,
    enabled: shouldSearchPreview,
  })

  useEffect(() => {
    // eslint-disable-next-line
    setSearchTerm(searchParams.get('name') ?? '')
  }, [searchParams])

  function handleSearch(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    if (!trimmedSearchTerm) {
      navigate('/categorias')
      return
    }

    const categorySlug = getCategorySlugFromSearchTerm(trimmedSearchTerm)

    if (categorySlug) {
      navigate(`/categorias/${categorySlug}?name=${encodeURIComponent(trimmedSearchTerm)}`)
      return
    }

    navigate(`/categorias?name=${encodeURIComponent(trimmedSearchTerm)}`)
  }

  function handleClearSearch() {
    setSearchTerm('')
    inputRef.current?.focus()
  }

  return (
    <form onSubmit={handleSearch} className="relative flex w-196 items-center">
      <input
        ref={inputRef}
        type="search"
        value={searchTerm}
        onChange={(event) => setSearchTerm(event.target.value)}
        onFocus={() => setIsInputFocused(true)}
        onBlur={() => setIsInputFocused(false)}
        placeholder="Buscar curso"
        className="search-input focus:border-cyan/50 w-full rounded-2xl border border-white/10 bg-white/10 py-3 pr-22 pl-5 text-sm text-white transition outline-none placeholder:text-white/40 focus:bg-white/15"
      />

      {searchTerm.length > 0 ? (
        <button
          type="button"
          onMouseDown={(event) => event.preventDefault()}
          onClick={handleClearSearch}
          className="absolute right-12 flex h-8 w-8 items-center justify-center rounded-lg text-red-400 transition hover:bg-white/10 hover:text-red-300"
        >
          <X size={14} />
        </button>
      ) : null}

      <button
        type="submit"
        className="text-cyan absolute right-2 flex h-10 w-10 items-center justify-center rounded-xl transition hover:bg-white/10"
        aria-label="Buscar cursos"
      >
        <Search size={18} />
      </button>

      {showPreview ? (
        <div className="absolute top-full right-0 left-0 z-50 mt-2 overflow-hidden rounded-2xl border border-white/10 bg-[#14082f] shadow-2xl">
          {previewLoading ? (
            <p className="px-4 py-3 text-sm text-white/60">Buscando cursos...</p>
          ) : previewError ? (
            <p className="px-4 py-3 text-sm text-red-300">Erro ao buscar cursos.</p>
          ) : previewCourses.length > 0 ? (
            <ul className="home-trending-scrollbar max-h-80 overflow-y-auto">
              {previewCourses.map((course) => (
                <li key={course.id} className="border-b border-white/8 last:border-b-0">
                  <button
                    type="button"
                    onMouseDown={(event) => event.preventDefault()}
                    onClick={() => {
                      setSelectedCourse(course)
                      setIsInputFocused(false)
                    }}
                    className="flex w-full items-center gap-3 px-4 py-3 text-left transition hover:bg-white/6"
                  >
                    <img
                      src={`https://img.youtube.com/vi/${course.firstLessonYoutubeLink}/mqdefault.jpg`}
                      alt={course.name.name}
                      className="h-14 w-24 shrink-0 rounded-md object-cover"
                    />
                    <p className="wrap-break-words text-sm text-white">{course.name.name}</p>
                  </button>
                </li>
              ))}
            </ul>
          ) : (
            <p className="px-4 py-3 text-sm text-white/60">Nenhum curso encontrado.</p>
          )}
        </div>
      ) : null}

      <CourseModal
        course={selectedCourse}
        onClose={() => setSelectedCourse(null)}
        actionLabel="Inscrever-se"
      />
    </form>
  )
}
