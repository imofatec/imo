export default function SkeletonVerAula() {
    return (
      <div className="max-w-full min-h-screen animate-pulse">
        <div className="h-10 w-64 bg-gray-700 rounded mx-8 my-6" />
  
        <div className="flex flex-row">
          <div className="flex flex-col w-3/4 p-8 space-y-6">
            <div className="w-full h-[30rem] bg-gray-700 rounded-lg" />
            <div className="space-y-4">
              <div className="h-6 w-1/3 bg-gray-600 rounded" />
              <div className="h-4 w-full bg-gray-600 rounded" />
              <div className="h-4 w-full bg-gray-600 rounded" />
              <div className="h-4 w-2/3 bg-gray-600 rounded" />
            </div>
            <div className="space-y-4">
              <div className="h-6 w-1/3 bg-gray-600 rounded" />
              <div className="h-20 w-full bg-gray-700 rounded" />
            </div>
          </div>
          <div className="flex flex-col w-1/4 pl-4 bg-custom-dark-blue p-6 max-h-[calc(100vh-4rem)] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-500 scrollbar-track-gray-800 space-y-4">
            <div className="h-6 w-1/2 bg-gray-600 rounded mx-auto" />
            {Array.from({ length: 4 }).map((_, i) => (
              <div
                key={i}
                className="flex gap-4 p-2 border border-gray-600 rounded bg-gray-800"
              >
                <div className="w-20 h-14 bg-gray-700 rounded" />
                <div className="flex-1 space-y-2">
                  <div className="h-4 bg-gray-600 rounded w-3/4" />
                  <div className="h-3 bg-gray-600 rounded w-1/2" />
                </div>
              </div>
            ))}
            <div className="h-10 bg-gray-700 rounded mt-4" />
          </div>
        </div>
      </div>
    )
  }
  