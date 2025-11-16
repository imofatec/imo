export default function SkeletonMyCourses() {
    return (
      <>
        <div className="h-6 w-1/3 bg-gray-700 rounded mt-10 mb-10 mx-12 animate-pulse" />
  
        <div className="min-h-screen flex flex-row w-full animate-pulse">
          <div className="w-1/3 p-12 space-y-4">
            {Array.from({ length: 6 }).map((_, i) => (
              <div key={i} className="h-6 w-2/3 bg-gray-700 rounded" />
            ))}
          </div>
          <div className="w-2/3 p-12">
            <div className="flex flex-row w-full mb-8">
              <div className="flex flex-col w-1/2">
                <div className="h-6 w-1/2 bg-gray-700 rounded" />
              </div>
              <div className="flex flex-col w-1/2 items-end">
                <div className="h-8 w-36 bg-gray-700 rounded" />
              </div>
            </div>
            <div className="flex flex-wrap gap-6">
              {Array.from({ length: 8 }).map((_, i) => (
                <div
                  key={i}
                  className="w-[300px] h-[340px] bg-gray-700 rounded-xl shadow-md"
                >
                  <div className="h-[160px] bg-gray-600 rounded-t-xl" />
                  <div className="p-4 space-y-3">
                    <div className="h-4 w-3/4 bg-gray-600 rounded" />
                    <div className="h-3 w-5/6 bg-gray-600 rounded" />
                    <div className="h-3 w-2/3 bg-gray-600 rounded" />
                    <div className="h-8 w-1/2 bg-gray-600 rounded mt-4" />
                  </div>
                </div>
              ))}
            </div>
            <div className="flex justify-center items-center gap-4 mt-10">
              <div className="h-8 w-24 bg-gray-700 rounded" />
              <div className="h-8 w-24 bg-gray-700 rounded" />
            </div>
          </div>
        </div>
      </>
    )
  }
  