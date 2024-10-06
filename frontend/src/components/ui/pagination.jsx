export default function Pagination({ page, hasMoreCourses, onPrevious, onNext }){
    return (
        <div className="flex justify-center items-center mt-6">
            <button
              onClick={onPrevious}
              disabled={page === 0}
              className={`px-4 py-2 mr-2 ${
                page === 0 ? 'opacity-50 cursor-not-allowed' : ''
              }`}
            >
              &lt;
            </button>
            <span className="text-white">{page + 1}</span>
            <button
              onClick={onNext}
              disabled={!hasMoreCourses}
              className={`px-4 py-2 ml-2 ${
                !hasMoreCourses ? 'opacity-50 cursor-not-allowed' : ''
              }`}
            >
              &gt;
            </button>
          </div>
    )
}