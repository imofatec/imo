import { Input } from '@/components/ui/inputs/input'
import { Search } from 'lucide-react'
import axios from 'axios'
import { safeAwait } from '@/lib/safeAwait'
import { useRef, useState, useEffect } from 'react'
import { Link } from 'react-router-dom'

export default function DropdownSearch() {
  const [searchedCourses, setSearchedCourses] = useState([])
  const [searchedWord, setSearchedWord] = useState('')
  const [isSearching, setIsSearching] = useState(false)
  const [inputValue, setInputValue] = useState('')
  const dropdownSearchRef = useRef(null)
  const inputRef = useRef(null)

  const handleChange = async (e) => {
    const courseName = e.target.value
    setInputValue(courseName)

    if (courseName.length < 2) {
      setSearchedCourses([])
      return
    }

    setTimeout(async () => {
      const [error, result] = await safeAwait(
        axios.get(
          `/api/courses/pagination/get-all/overviews/cn/${courseName}?page=0&size=10`,
        ),
      )
      if (result?.data) {
        setSearchedWord(courseName)
        setSearchedCourses(result.data)
      }
    }, 400)
  }

  const highlightText = (text) => {
    const pattern = new RegExp(`(${searchedWord})`, 'gi')

    const parts = text.split(pattern)

    return parts.map((part, i) =>
      part.toLowerCase() === searchedWord.toLowerCase() ? (
        <span key={i} className="bg-custom-header-cyan text-black w-auto">
          {part}
        </span>
      ) : (
        part
      ),
    )
  }

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        dropdownSearchRef.current &&
        !dropdownSearchRef.current.contains(event.target) &&
        !inputRef.current.contains(event.target)
      ) {
        setIsSearching(false)
      }
    }

    document.addEventListener('mousedown', handleClickOutside)

    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [])

  // const handleSearch = (e) => {
  //   if (e.key === 'Enter') {
  //     const searchTerm = e.target.value
  //       .trim()
  //       .toLowerCase()
  //       .replace(/[^\w\s]|_/g, '')
  //       .replace(/\s+/g, '-')
  //     navigate(`/categorias/${searchTerm}`)
  //     e.preventDefault()
  //   }
  // }

  return (
    <>
      <Search className="absolute left-3 text-white" />
      <div className="absolute left-12 w-[1px] h-5 bg-white border focus:boder-white"></div>
      <Input
        ref={inputRef}
        value={inputValue}
        type="search"
        placeholder="Pesquise por um curso"
        className="pl-14 bg-custom-search-dark border-custom-search-dark text-white w-full placeholder:text-white h-[2.375rem]"
        // onKeyDown={handleSearch}
        onClick={() => setIsSearching(true)}
        onChange={handleChange}
      />

      {isSearching && (
        <div
          ref={dropdownSearchRef}
          className="absolute z-10 top-[2.7rem] w-[30rem] min-h-[5rem] max-h-[30rem] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-500 scrollbar-track-gray-800 space-y-6 bg-custom-header-dark-purple border border-white text-left"
        >
          <div className="mt-5"></div>
          {searchedCourses?.map((course, i) => (
            <p key={i} className="inline-flex items-center gap-5 mx-10">
              {course && (
                <>
                  <img
                    src={`https://img.youtube.com/vi/${course.firstLessonYoutubeId}/maxresdefault.jpg`}
                    width="100px"
                  />

                  <Link
                    to={`/cursos/${course.slugCourse}/${course.firstLessonYoutubeId}`}
                    onClick={() => {
                      setIsSearching(false),
                        setInputValue(''),
                        setSearchedCourses([]),
                        setSearchedWord('')
                    }}
                    className="inline-flex flex-col"
                  >
                    <span className="hover:underline">
                      {highlightText(course.name)}
                    </span>
                    <span className="text-gray-400 text-sm">
                      Quantidade de aulas: {course.totalLessons}
                    </span>
                  </Link>
                </>
              )}
            </p>
          ))}
          <div className="mb-5"></div>
        </div>
      )}
    </>
  )
}
