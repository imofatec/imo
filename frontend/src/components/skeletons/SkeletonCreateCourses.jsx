export default function SkeletonCreateCourses() {
    return (
      <div className="flex justify-center">
        <div className="w-[70rem]">
          <div className="w-full border-white border rounded-xl p-6 px-10 my-6 space-y-4">
            <div className="space-y-2">
              <div className="w-1/3 h-4 bg-gray-700 rounded animate-pulse" />
              <div className="w-full h-10 bg-gray-700 rounded animate-pulse" />
            </div>
            <div className="space-y-2">
              <div className="w-1/3 h-4 bg-gray-700 rounded animate-pulse" />
              <div className="w-full h-10 bg-gray-700 rounded animate-pulse" />
            </div>
            <div className="space-y-2">
              <div className="w-1/3 h-4 bg-gray-700 rounded animate-pulse" />
              <div className="w-full h-24 bg-gray-700 rounded animate-pulse" />
            </div>
          </div>
          <div className="w-full border-white border rounded-xl p-6 px-10 my-6 space-y-2">
            <div className="w-1/4 h-4 bg-gray-700 rounded animate-pulse" />
            <div className="w-full h-20 bg-gray-700 rounded animate-pulse" />
            <div className="w-full h-20 bg-gray-700 rounded animate-pulse" />
          </div>
          <div className="flex flex-row justify-end w-full">
            <div className="w-32 h-10 bg-gray-700 rounded animate-pulse mr-10" />
          </div>
        </div>
      </div>
    )
  }
  