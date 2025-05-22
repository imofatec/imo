export default function SkeletonAccountSettings() {
    return (
      <div className="h-screen mx-28 mt-16 animate-pulse">
        <div className="mb-6">
          <div className="h-8 w-1/4 bg-gray-700 rounded mb-2" />
          <div className="h-4 w-1/2 bg-gray-700 rounded" />
          <div className="h-px bg-gray-700 my-4" />
        </div>
  
        <div className="flex flex-row">
          <div className="flex flex-col w-1/4 items-center justify-between mr-14">
            <div className="flex flex-col items-center gap-y-6">
              <div className="w-32 h-32 bg-gray-700 rounded-full" />
              <div className="w-40 h-10 bg-gray-700 rounded" />
            </div>
  
            <div className="space-y-4 text-center mt-6">
              <div className="w-[18rem] h-10 bg-gray-700 rounded" />
              <div className="w-4/5 h-4 bg-gray-700 rounded mx-auto" />
            </div>
          </div>
          <div className="flex flex-col justify-between w-3/4 ml-14">
            <div className="flex flex-row justify-center gap-x-14 w-full mt-10">
              <div className="flex flex-col w-1/2">
                <div className="h-5 w-1/3 bg-gray-700 rounded mb-2" />
                <div className="h-10 w-full bg-gray-700 rounded mb-4" />
              </div>
              <div className="flex flex-col w-1/2">
                <div className="h-5 w-1/3 bg-gray-700 rounded mb-2" />
                <div className="h-10 w-full bg-gray-700 rounded mb-4" />
              </div>
            </div>
  
            <div className="flex flex-row justify-center gap-x-14 w-full">
              <div className="flex flex-col w-1/2">
                <div className="h-5 w-1/3 bg-gray-700 rounded mb-2" />
                <div className="h-10 w-full bg-gray-700 rounded mb-4" />
              </div>
              <div className="flex flex-col w-1/2">
                <div className="h-5 w-1/3 bg-gray-700 rounded mb-2" />
                <div className="h-10 w-full bg-gray-700 rounded mb-4" />
              </div>
            </div>
  
            <div className="flex flex-col items-center mt-4 mb-4">
              <div className="w-[18rem] h-10 bg-gray-700 rounded mb-4" />
              <div className="w-1/2 h-4 bg-gray-700 rounded" />
            </div>
          </div>
        </div>
      </div>
    )
  }
  